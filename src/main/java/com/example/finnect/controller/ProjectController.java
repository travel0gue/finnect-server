package com.example.finnect.controller;

import com.example.finnect.apiResponse.ApiResponse;
import com.example.finnect.dto.request.*;
import com.example.finnect.dto.response.ProjectResponse;
import com.example.finnect.dto.response.RewardResponse;
import com.example.finnect.entity.Project;
import com.example.finnect.entity.User;
import java.util.List;
import com.example.finnect.entity.enums.FundingType;
import com.example.finnect.repository.ProjectRepository;
import com.example.finnect.service.ProjectService;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Operation(summary = "프로젝트 삭제")
    public ApiResponse<Void> deleteProject(
            @PathVariable(name = "project_id") Long projectId,
            @AuthenticationPrincipal User currentUser
    ) {
        projectService.deleteProject(projectId, currentUser);
        return ApiResponse.onSuccess("프로젝트가 성공적으로 삭제되었습니다.");
    }

    @GetMapping("/my")
    @Operation(summary = "내 프로젝트 조회")
    public ApiResponse<Page<ProjectResponse>> getMyProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User currentUser) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId(), pageable);
        Page<ProjectResponse> projectResponses = projects.map(ProjectResponse::from);
        return ApiResponse.onSuccess(projectResponses);
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
    
    // 리워드 삭제 API (다형성 활용)
    @DeleteMapping("/rewards/{reward_id}")
    @Operation(summary = "리워드 삭제")
    public ApiResponse<Void> deleteReward(
            @PathVariable(name = "reward_id") Long rewardId,
            @AuthenticationPrincipal User currentUser
    ) {
        projectService.deleteReward(rewardId, currentUser);
        return ApiResponse.onSuccess("리워드가 성공적으로 삭제되었습니다.");
    }
    
    // 프로젝트 ID로 리워드 리스트 조회 API
    @GetMapping("/{project_id}/rewards")
    @Operation(summary = "프로젝트의 리워드 리스트 조회")
    public ApiResponse<List<RewardResponse>> getRewardsByProjectId(
            @PathVariable(name = "project_id") Long projectId
    ) {
        List<RewardResponse> rewards = projectService.getRewardsByProjectId(projectId);
        return ApiResponse.onSuccess(rewards);
    }

}