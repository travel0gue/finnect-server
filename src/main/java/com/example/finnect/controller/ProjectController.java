package com.example.finnect.controller;

import com.example.finnect.apiResponse.ApiResponse;
import com.example.finnect.dto.request.*;
import com.example.finnect.dto.response.ProjectResponse;
import com.example.finnect.dto.response.RewardResponse;
import com.example.finnect.entity.User;
import com.example.finnect.repository.ProjectRepository;
import com.example.finnect.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트", description = "프로젝트 관리 API")
@Slf4j
public class ProjectController {
    
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @PostMapping
    @Operation(summary = "프로젝트 생성")
    public ApiResponse<ProjectResponse> createProject(
            @Valid @RequestBody ProjectCreateRequest request,
            @AuthenticationPrincipal User currentUser) {
        ProjectResponse response = projectService.createProject(request, currentUser.getId());
        
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{project_id}")
    @Operation(summary = "프로젝트 수정")
    public ApiResponse<ProjectResponse> updateProject(
            @Valid @RequestBody ProjectCreateRequest request,
            @AuthenticationPrincipal User currentUser,
            @PathVariable(name = "project_id") Long projectId
    ) {
        ProjectResponse response = projectService.updateProject(request, currentUser, projectId);

        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/{project_id}/submit")
    @Operation(summary = "프로젝트 제출", description = "프로젝트 상태가 제출로 바뀝니다.")
    public ApiResponse<ProjectResponse> submitProject(
            @PathVariable(name = "project_id") Long projectId,
            @AuthenticationPrincipal User currentUser) {

        ProjectResponse response = projectService.submitProject(projectId, currentUser);
        
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{project_id}")
    public ApiResponse<Void> deleteProject(
            @PathVariable(name = "project_id") Long projectId,
            @AuthenticationPrincipal User currentUser
    ) {
        projectService.deleteProject(projectId, currentUser);
        return ApiResponse.onSuccess("프로젝트가 성공적으로 삭제되었습니다.");
    }
    
    // 리워드 생성 API (Request 기반)
    @PostMapping("/{project_id}/rewards/bond")
    @Operation(summary = "채권 리워드 생성")
    public ApiResponse<RewardResponse> createBondReward(
            @PathVariable(name = "project_id") Long projectId,
            @Valid @RequestBody BondRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.createBondReward(projectId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
    
    @PostMapping("/{project_id}/rewards/stock")
    @Operation(summary = "주식 리워드 생성")
    public ApiResponse<RewardResponse> createStockReward(
            @PathVariable(name = "project_id") Long projectId,
            @Valid @RequestBody StockRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.createStockReward(projectId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
    
    @PostMapping("/{project_id}/rewards/loan")
    @Operation(summary = "P2P 대출 리워드 생성")
    public ApiResponse<RewardResponse> createLoanRepaymentReward(
            @PathVariable(name = "project_id") Long projectId,
            @Valid @RequestBody LoanRepaymentRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.createLoanRepaymentReward(projectId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
    
    @PostMapping("/{project_id}/rewards/donation")
    @Operation(summary = "후원 리워드 생성")
    public ApiResponse<RewardResponse> createDonationReward(
            @PathVariable(name = "project_id") Long projectId,
            @Valid @RequestBody DonationRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.createDonationReward(projectId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }

    // 리워드 수정 API
    @PatchMapping("/rewards/{reward_id}/bond")
    @Operation(summary = "채권 리워드 수정")
    public ApiResponse<RewardResponse> updateBondReward(
            @PathVariable(name = "reward_id") Long rewardId,
            @Valid @RequestBody BondRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.updateBondReward(rewardId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
    
    @PatchMapping("/rewards/{reward_id}/stock")
    @Operation(summary = "주식 리워드 수정")
    public ApiResponse<RewardResponse> updateStockReward(
            @PathVariable(name = "reward_id") Long rewardId,
            @Valid @RequestBody StockRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.updateStockReward(rewardId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
    
    @PatchMapping("/rewards/{reward_id}/loan")
    @Operation(summary = "P2P 대출 리워드 수정")
    public ApiResponse<RewardResponse> updateLoanRepaymentReward(
            @PathVariable(name = "reward_id") Long rewardId,
            @Valid @RequestBody LoanRepaymentRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.updateLoanRepaymentReward(rewardId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
    
    @PatchMapping("/rewards/{reward_id}/donation")
    @Operation(summary = "후원 리워드 수정")
    public ApiResponse<RewardResponse> updateDonationReward(
            @PathVariable(name = "reward_id") Long rewardId,
            @Valid @RequestBody DonationRewardRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        RewardResponse response = projectService.updateDonationReward(rewardId, request, currentUser);
        return ApiResponse.onSuccess(response);
    }
}