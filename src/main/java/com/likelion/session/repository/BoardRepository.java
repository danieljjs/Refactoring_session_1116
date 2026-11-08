package com.likelion.session.repository;

import com.likelion.session.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 제목으로 검색 (포함)
    List<Board> findByTitleContaining(String keyword);

    // 작성자로 검색
    List<Board> findByWriter(String writer);

    // 제목 또는 내용으로 통합 검색 (@Query 사용)
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword% ORDER BY b.createdAt DESC")
    List<Board> searchByKeyword(@Param("keyword") String keyword);

    // 페이지네이션 적용 검색
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}