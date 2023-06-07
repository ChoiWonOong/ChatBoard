package com.example.Board.contoller;

import com.example.Board.dto.BoardDTO;
import com.example.Board.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private static BoardService boardService;
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String saveForm(){
        return "save";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "index";
    }
    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);

        return "redirect:/board/" + boardDTO.getId();
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }
    // /board/paging?page=?  default page = 1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
//        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        //보이는 페이지 최대 개수
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();// 3 6 9 12 15

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }
}
