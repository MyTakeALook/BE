package com.takealook.takealook.service;

import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.entity.Board;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.entity.Comment;
import com.takealook.takealook.entity.Liked;
import com.takealook.takealook.repository.BoardRepository;
import com.takealook.takealook.repository.LikeRepository;
import com.takealook.takealook.repository.UserRepository;
import com.takealook.takealook.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository; //
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, UserDetailsImpl userDetailsImpl) throws IOException {
        //String fullPath = "/C:\\Users\\USER\\Desktop\\hanghae99\\springmvc\\BE\\src\\main\\resources\\static\\img/" + file.getOriginalFilename();
        String imageurl = "img/"; // + file.getOriginalFilename();
        //file.transferTo(new File(fullPath)); // IOException 처리 해줘야함

        Board board = new Board(boardRequestDto, imageurl, userDetailsImpl.getUser());

        boardRepository.save(board);

        List<Liked> likedList = likeRepository.findAllByBoard(board);

        BoardResponseDto boardResponseDto = new BoardResponseDto(board, Long.valueOf(likedList.size()));
        return boardResponseDto;
    }

    public List<BoardResponseDto> readBoardRankList() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtAsc();
        List<BoardResponseDto> boardResponseRankDtoList = new ArrayList<>();

        ArrayList<Long> tempBoardRank = new ArrayList<>();

        for (Board board : boardList) {
            if (board.isDelete()) {
                tempBoardRank.add(-1L);
                continue;
            }

            List<Liked> likedList = likeRepository.findAllByBoard(board);
            tempBoardRank.add(Long.valueOf(likedList.size()));
        }

        ArrayList<Integer> boardRank = new ArrayList<>();

        for (int i=0; i<3; i++) {
            Long likeMax = Collections.max(tempBoardRank);
            int witch = tempBoardRank.indexOf(likeMax);
            boardRank.add(witch);
            tempBoardRank.set(witch, -1L);
        }

        for (Integer integer : boardRank) {
            Optional<Board> board = boardRepository.findByBoardId(Long.valueOf(integer)+1);
            List<Liked> likedList = likeRepository.findAllByBoard(board.get());
            BoardResponseDto boardResponseDto = new BoardResponseDto(board.get(), Long.valueOf(likedList.size()));
            boardResponseRankDtoList.add(boardResponseDto);
        }

        return boardResponseRankDtoList;
    }

    public List<BoardResponseDto> readBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtAsc();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for (Board board : boardList) {
            if (board.isDelete()) {
                continue;
            }

            List<Liked> likedList = likeRepository.findAllByBoard(board);

            BoardResponseDto boardResponseDto = new BoardResponseDto(board, Long.valueOf(likedList.size()));
            boardResponseDtoList.add(boardResponseDto);
        }

        return boardResponseDtoList;
    }

    public BoardResponseDto readBoard(Long boardId) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);

        if (board.get().isDelete()) {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        if (board.isPresent()){
            board.get().BoardVisit(); //잘 작동함. board에 visit 값이 올라감. 그럼 뭐가 문제? 리스폰스문제일까
            boardRepository.save(board.get());  // 저장을 안해서 자꾸만 visit가 0으로 초기화됐었다 !! 저장을 했더니 해결
        }
        List<Liked> likedList = likeRepository.findAllByBoard(board.get());

        BoardResponseDto boardResponseDto = new BoardResponseDto(board.get(), Long.valueOf(likedList.size()));
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto patchBoard(Long boardId, BoardRequestDto boardRequestDto, UserDetailsImpl userDetailsImpl) throws IOException {
        Optional<Board> board = boardRepository.findByBoardId(boardId);

        if (board.get().isDelete()) {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        if (!board.get().getUser().getId().equals(userDetailsImpl.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        //        String fullPath = "/C:\\Users\\USER\\Desktop\\hanghae99\\springmvc\\BE\\src\\main\\resources\\static\\img/" + file.getOriginalFilename();
                String imageurl = "img/"; // + file.getOriginalFilename();
        //        file.transferTo(new File(fullPath)); // IOException 처리 해줘야함

        board.get().BoardPatch(boardRequestDto, imageurl);

        List<Liked> likedList = likeRepository.findAllByBoard(board.get());

        BoardResponseDto boardResponseDto = new BoardResponseDto(board.get(), Long.valueOf(likedList.size()));
        return boardResponseDto;
    }

    @Transactional
    public ResponseDto deleteBoard(Long boardId, UserDetailsImpl userDetailsImpl) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        if (!board.get().isDelete()) {
            board.get().BoardDelete();
            List<Comment> commentList = board.get().getComment();
            for (Comment comment : commentList) {
                comment.CommentDelete();
            }
        } else {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        if (!board.get().getUser().getId().equals(userDetailsImpl.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        board.get().BoardDelete();
        ResponseDto responseDto = new ResponseDto();
        responseDto.ResponseTrue();
        return responseDto;
    }

    @Transactional
    public ResponseDto likeBoard(Long boardId, UserDetailsImpl userDetailsImpl) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);

        if (likeRepository.findByUserAndBoard(userDetailsImpl.getUser(), board.get()).isPresent()) {
            Optional<Liked> liked = likeRepository.findByUserAndBoard(userDetailsImpl.getUser(), board.get());
            likeRepository.delete(liked.get());
        } else {
            Liked liked = new Liked(userDetailsImpl.getUser(), board.get());
            likeRepository.save(liked);
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.ResponseTrue();
        return responseDto;
    }

}
