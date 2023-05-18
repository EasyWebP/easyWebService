package easyweb.easywebservice.domain.Member.api;

import easyweb.easywebservice.domain.Member.application.MemberService;
import easyweb.easywebservice.domain.Member.dto.MemberDTO;
import easyweb.easywebservice.domain.Member.dto.NativeQ.MemberInfoQ;
import easyweb.easywebservice.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static easyweb.easywebservice.domain.Member.dto.MemberDTO.*;

@Tag(name = "멤버 정보", description = "멤버 정보 관련 API 입니다.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "로그인한 멤버 정보 조회",description = "로그인한 멤버 정보 조회 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = MemberInfoQ.class)))
    })
    @GetMapping(value = "")
    public ResponseEntity<MemberInfoQ> getLoginMemberInfo() {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(memberService.getLoginMemberInfo(currentMemberId));
    }

    @Operation(summary = "멤버 정보 업데이트", description = "멤버 정보 업데이트 API 입니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공시", content = @Content(schema = @Schema(implementation = MemberInfoQ.class)))
    })
    @PatchMapping(value = "")
    public ResponseEntity<MemberUpdateDto> updateMemberInfo(@RequestBody Map<String, Object> updates) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(memberService.updateMemberInfo(updates, currentMemberId));
    }
}
