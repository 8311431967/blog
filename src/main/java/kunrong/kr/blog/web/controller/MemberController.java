package kunrong.kr.blog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kunrong.kr.blog.bl.service.MemberService;
import kunrong.kr.blog.bl.vo.MemberVo;
import kunrong.kr.blog.bl.vo.ResultVo;
import kunrong.kr.blog.exception.BlogException;
import kunrong.kr.blog.util.constant.ErrorCode;
import kunrong.kr.blog.util.enums.Gender;
import kunrong.kr.blog.web.json.*;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
@Api(value = "/member", description = "Application member API")
@RestController
@RequestMapping("Api/v1/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @ApiOperation(value = "Get member list",notes = "Get a page of memberVo",
            response = Page.class, produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "pageJson", value = "page setting", required = true, dataType = "PageJson")
    @PostMapping(value = "/list", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<MemberVo> memberList(@Valid @RequestBody PageJson pageJson) {
        return memberService.getMemberList(pageJson.getPage(),pageJson.getPageSize());
    }

    @ApiOperation(value = "Get member info",notes = "Get detail info of a member.",
            response = MemberVo.class,responseContainer = "ResultVo", produces = "application/json;charset=UTF-8")
    @GetMapping(value = "/{id}",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MemberVo info(@PathVariable int id) {
        return memberService.getMemberInfo(id);
    }

    @ApiOperation(value = "Create member",notes = "Create a new user with member authority.",
            response = ResultVo.class, produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "memberJson", value = "member data", required = true, dataType = "MemberJson")
    @PostMapping(value = "",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<MemberVo> createMember(@Valid @RequestBody MemberJson memberJson) {
        return new ResultVo<>(ErrorCode.SUCCESS,null,memberService.createMember(memberJson.getName(),
                memberJson.getPassword(),memberJson.getPhone(),memberJson.getAvatar(),
                Gender.valueOf(memberJson.getGender()),memberJson.getDescription()));
    }

    @ApiOperation(value = "Edit member",notes = "Edit member info.",
            response = ResultVo.class, produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "memberEditJson", value = "member edit data", required = true, dataType = "MemberEditJson")
    @PostMapping(value = "/edit",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<MemberVo> editMember(@Valid @RequestBody MemberEditJson memberEditJson) {
        return new ResultVo<>(ErrorCode.SUCCESS,null,memberService.editMember(memberEditJson.getMemberId(),
                memberEditJson.getPassword(),memberEditJson.getAvatar(),Gender.valueOf(memberEditJson.getGender()),
                memberEditJson.getDescription()));
    }

    @ApiOperation(value = "Recharge member account",notes = "Transfer money from bank account to member account.",
            response = ResultVo.class, produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "transferJson", value = "transfer data", required = true, dataType = "TransferJson")
    @PostMapping(value = "/recharge", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<MemberVo> recharge(@Valid @RequestBody TransferJson transferJson) throws BlogException {
        return memberService.transferToRemain(transferJson.getMemberId(),transferJson.getAccountId(),transferJson.getMoney());
    }

    @ApiOperation(value = "Stop member",notes = "Stop member.",
            response = ResultVo.class, produces = "application/json;charset=UTF-8")
    @PostMapping(value = "/{id}/stop", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<MemberVo> stop(@PathVariable int id) throws BlogException {
        return memberService.stopMember(id);
    }

    @ApiOperation(value = "Exchange member score",notes = "Exchange member score for member balance.",
            response = ResultVo.class, produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "exchangeJson", value = "exchange number", required = true, dataType = "ExchangeJson")
    @PostMapping(value = "/{id}/exchange", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<MemberVo> exchangeScore(@PathVariable int id, @Valid @RequestBody ExchangeJson exchangeJson) throws BlogException {
        return memberService.exchangeScore(id,exchangeJson.getScore());
    }


}
