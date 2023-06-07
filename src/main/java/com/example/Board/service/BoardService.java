package com.example.Board.service;

import com.example.Board.dto.BoardDTO;

import com.example.Board.entity.BoardEntity;
import com.example.Board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }
    public void save(BoardDTO boardDTO){
        // Logged In User Information
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardEntity.setUsername(username);
        boardRepository.save(boardEntity);
    }
    // Show List
    public List<BoardDTO> findAll(){
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }
    @Transactional
    // 조회수 증가
    public void updateHits(Long id){
        boardRepository.updateHits(id);
    }
    public BoardDTO findById(Long id){
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        }
        else{
            return null;
        }
    }
    public BoardDTO update(BoardDTO boardDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        BoardDTO cmpBoardDTO = findById(boardDTO.getId());
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardEntity.setUsername(cmpBoardDTO.username);
        if(username.equals(cmpBoardDTO.username)){
            boardRepository.save(boardEntity);
            return cmpBoardDTO;
        }
        return null;
    }
    public void delete(Long id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        BoardEntity boardEntity = boardRepository.findById(id).get();
        String entityUsername = boardEntity.getUsername();
        if(username.equals(entityUsername)){
            boardRepository.deleteById(id);
        }
    }
    public Page<BoardDTO> paging(Pageable pageable){
        int page = pageable.getPageNumber()-1;
        int pageLimit = 3;  // 한 페이지에 들어갈 게시글 수
        // 정렬 기준은 id 기준 내림차순
        // page 위치에 있는 값은 0 부터 시작, so page - 1
        Page<BoardEntity> boardEntities =
        boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        /*  Page 객체 사용 가능 메소드
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수 -> page limit
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부
         */

        //목록 : id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(
                board-> new BoardDTO(board.getId(), board.getUsername(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}
