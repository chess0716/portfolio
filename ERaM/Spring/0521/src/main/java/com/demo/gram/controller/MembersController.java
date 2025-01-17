package com.demo.gram.controller;

import com.demo.gram.dto.MembersDTO;
import com.demo.gram.entity.Members;
import com.demo.gram.service.MembersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/members/")
@RequiredArgsConstructor
public class MembersController {

  private final MembersService membersService;

  @PutMapping(value = "/update", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> update(@RequestBody MembersDTO membersDTO) {
    log.info("update..."+membersDTO);

    membersService.updateMembersDTO(membersDTO);
    return new ResponseEntity<>("modified", HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> delete(@PathVariable("num") Long num) {
    log.info("delete...");

    membersService.removeMembers(num);
    return new ResponseEntity<>("removed", HttpStatus.OK);
  }

  @GetMapping(value = "/get/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MembersDTO> read(@PathVariable("num") Long num) {
    log.info("read..."+num);

    return new ResponseEntity<>(membersService.get(num), HttpStatus.OK);
  }

  @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MembersDTO>> getAll() {
    log.info("getList...");

    return new ResponseEntity<>(membersService.getAll(), HttpStatus.OK);
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @CrossOrigin(origins = "http://localhost:3000")
  public ResponseEntity<Members> getCurrentUser(Principal principal) {
    log.info("getCurrentUser...");

    if (principal == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    String email = principal.getName(); // 현재 인증된 사용자의 이메일을 얻음
    Members user = membersService.findByEmail(email);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
