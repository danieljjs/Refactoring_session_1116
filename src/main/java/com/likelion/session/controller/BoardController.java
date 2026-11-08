package com.likelion.session.controller;

import com.likelion.session.dto.BoardCreateRequest;
import com.likelion.session.dto.BoardResponse;
import com.likelion.session.dto.BoardUpdateRequest;
import com.likelion.session.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.likelion.session.dto.BoardPageResponse;
import com.likelion.session.dto.DeleteLogResponse;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /*
        게시글 생성

        [요청 흐름]
        Client
        -> DispatcherServlet
        -> HandlerMapping
        -> BoardController의 create() 메서드 선택
        -> Service 호출
        -> Repository 호출
        -> DB 저장
        -> 결과 반환
        -> JSON 응답
     */
    @Operation(
            summary = "게시글 생성",
            description = "새로운 게시글을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<BoardResponse> create(@RequestBody BoardCreateRequest request) {
        BoardResponse response = boardService.create(request);
        return ResponseEntity.ok(response);
    }

    // 게시글 전체 조회
    @Operation(
            summary = "게시글 전체 조회",
            description = "등록된 모든 게시글을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<List<BoardResponse>> findAll() {
        List<BoardResponse> response = boardService.findAll();
        return ResponseEntity.ok(response);
    }

    // 게시글 단건 조회
    @Operation(
            summary = "게시글 단건 조회",
            description = "id로 특정 게시글을 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findById(@PathVariable Long id) {
        BoardResponse response = boardService.findById(id);
        return ResponseEntity.ok(response);
    }

    // 게시글 수정
    @Operation(
            summary = "게시글 수정",
            description = "id로 특정 게시글의 제목과 내용을 수정합니다."
    )
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long id,
                                                @RequestBody BoardUpdateRequest request) {
        BoardResponse response = boardService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @Operation(
            summary = "게시글 삭제",
            description = "id로 특정 게시글을 삭제합니다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // BoardController.java 에 아래 메서드들 추가

    // 제목으로 검색
    @Operation(
            summary = "제목으로 게시글 검색",
            description = "제목에 키워드가 포함된 게시글을 조회합니다."
    )
    @GetMapping("/search/title")
    public ResponseEntity<List<BoardResponse>> searchByTitle(@RequestParam String keyword) {
        List<BoardResponse> response = boardService.searchByTitle(keyword);
        return ResponseEntity.ok(response);
    }

    // 작성자로 검색
    @Operation(
            summary = "작성자로 게시글 검색",
            description = "특정 작성자의 게시글을 조회합니다."
    )
    @GetMapping("/search/writer")
    public ResponseEntity<List<BoardResponse>> searchByWriter(@RequestParam String writer) {
        List<BoardResponse> response = boardService.searchByWriter(writer);
        return ResponseEntity.ok(response);
    }

    // 통합 검색
    @Operation(
            summary = "게시글 통합 검색",
            description = "제목 또는 내용에 키워드가 포함된 게시글을 조회합니다."
    )
    @GetMapping("/search")
    public ResponseEntity<List<BoardResponse>> search(@RequestParam String keyword) {
        List<BoardResponse> response = boardService.search(keyword);
        return ResponseEntity.ok(response);
    }

    // 페이지네이션 전체 조회
    @Operation(
            summary = "게시글 페이지 조회",
            description = "페이지네이션이 적용된 게시글 목록을 조회합니다."
    )
    @GetMapping("/page")
    public ResponseEntity<BoardPageResponse> findAllWithPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        BoardPageResponse response = boardService.findAllWithPage(page, size);
        return ResponseEntity.ok(response);
    }

    // 삭제 이력 조회
    @Operation(summary = "삭제 이력 조회", description = "삭제된 게시글의 이력을 조회합니다.")
    @GetMapping("/delete-logs")
    public ResponseEntity<List<DeleteLogResponse>> findAllDeleteLogs() {
        return ResponseEntity.ok(boardService.findAllDeleteLogs());
    }
}