package com.demo.gram.repository;

import com.demo.gram.entity.ChatRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  @Query("SELECT cr FROM ChatRoom cr WHERE cr.post.id = :postId")
  Optional<ChatRoom> findByPostId(@Param("postId") Long postId);
}
