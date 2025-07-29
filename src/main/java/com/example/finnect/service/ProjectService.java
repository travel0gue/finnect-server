package com.example.finnect.service;

import com.example.finnect.apiResponse.ErrorStatus;
import com.example.finnect.dto.request.*;
import com.example.finnect.dto.response.ProjectResponse;
import com.example.finnect.dto.response.RewardResponse;
import com.example.finnect.entity.*;
import com.example.finnect.entity.enums.ProjectStatus;
import com.example.finnect.exception.CustomException;
import com.example.finnect.repository.ProjectRepository;
import com.example.finnect.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final RewardRepository rewardRepository;

    public ProjectResponse createProject(ProjectCreateRequest request, Long userId) {
        
        Project draftProject =  Project.create(request, userId);
        Project project = projectRepository.save(draftProject);
        log.info("프로젝트가 생성되었습니다. ID: {}", project.getId());

        return ProjectResponse.from(project);
    }
    
    // 제출 기능
    public ProjectResponse submitProject(Long projectId, User user) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));

        //프로젝트 주인이 아닌 경우
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }

        project.setStatus(ProjectStatus.SUBMITTED);
        
        return ProjectResponse.from(project);
    }
    
    // 제출 검증 (모든 필수 필드 검증)
    private void validateProjectRequest(ProjectCreateRequest request) {
        if (request.getFundingStartDate().isAfter(request.getFundingEndDate())) {
            throw new IllegalArgumentException("펀딩 시작일은 종료일보다 이전이어야 합니다.");
        }
        
        if (request.getMaxInvestment() != null && 
            request.getMinInvestment() != null &&
            request.getMaxInvestment().compareTo(request.getMinInvestment()) < 0) {
            throw new IllegalArgumentException("최대 투자 금액은 최소 투자 금액보다 커야 합니다.");
        }
        
        if (request.getMinInvestment() != null && 
            request.getMinInvestment().compareTo(request.getTargetAmount()) >= 0) {
            throw new IllegalArgumentException("최소 투자 금액은 목표 금액보다 작아야 합니다.");
        }
    }

    public ProjectResponse updateProject(ProjectCreateRequest request, User user, Long projectId) {
        validateProjectRequest(request);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));

        //프로젝트 주인이 아닌 경우
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }

        project.updateProjectInfo(request);
        return ProjectResponse.from(projectRepository.save(project));
    }

    public void deleteProject(Long projectId, User user) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));

        //프로젝트 주인이 아닌 경우
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }

        projectRepository.delete(project);
    }
    
    // 리워드 수정 기능
    public RewardResponse updateBondReward(Long rewardId, BondRewardRequest request, User user) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_REWARD));
        
        // 프로젝트 소유자 확인
        if (!reward.getProject().isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!reward.getProject().isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        if (!(reward instanceof BondReward)) {
            throw new IllegalArgumentException("잘못된 리워드 타입입니다.");
        }
        
        BondReward bondReward = (BondReward) reward;
        bondReward.updateBondReward(request);
        
        return RewardResponse.from(rewardRepository.save(bondReward));
    }
    
    public RewardResponse updateStockReward(Long rewardId, StockRewardRequest request, User user) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_REWARD));
        
        // 프로젝트 소유자 확인
        if (!reward.getProject().isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!reward.getProject().isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        if (!(reward instanceof StockReward)) {
            throw new IllegalArgumentException("잘못된 리워드 타입입니다.");
        }
        
        StockReward stockReward = (StockReward) reward;
        stockReward.updateStockReward(request);
        
        return RewardResponse.from(rewardRepository.save(stockReward));
    }
    
    public RewardResponse updateLoanRepaymentReward(Long rewardId, LoanRepaymentRewardRequest request, User user) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_REWARD));
        
        // 프로젝트 소유자 확인
        if (!reward.getProject().isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!reward.getProject().isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        if (!(reward instanceof LoanRepaymentReward)) {
            throw new IllegalArgumentException("잘못된 리워드 타입입니다.");
        }
        
        LoanRepaymentReward loanReward = (LoanRepaymentReward) reward;
        loanReward.updateLoanRepaymentReward(request);
        
        return RewardResponse.from(rewardRepository.save(loanReward));
    }
    
    public RewardResponse updateDonationReward(Long rewardId, DonationRewardRequest request, User user) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_REWARD));
        
        // 프로젝트 소유자 확인
        if (!reward.getProject().isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!reward.getProject().isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        if (!(reward instanceof DonationReward)) {
            throw new IllegalArgumentException("잘못된 리워드 타입입니다.");
        }
        
        DonationReward donationReward = (DonationReward) reward;
        donationReward.updateDonationReward(request);
        
        return RewardResponse.from(rewardRepository.save(donationReward));
    }
    
    // 리워드 생성 기능
    public RewardResponse createReward(Long projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));
        
        // 프로젝트 소유자 확인
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!project.isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        // 펀딩 타입에 맞는 리워드 초안 생성
        Reward draftReward = createBasicDraft(project);
        Reward savedReward = rewardRepository.save(draftReward);
        
        log.info("리워드 초안이 생성되었습니다. Project ID: {}, Reward ID: {}", projectId, savedReward.getId());
        
        return RewardResponse.from(savedReward);
    }
    
    // 리워드 생성 기능 (Request 기반)
    public RewardResponse createBondReward(Long projectId, BondRewardRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));
        
        // 프로젝트 소유자 확인
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!project.isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        BondReward bondReward = BondReward.create(project, request);
        BondReward savedReward = rewardRepository.save(bondReward);
        
        log.info("채권 리워드가 생성되었습니다. Project ID: {}, Reward ID: {}", projectId, savedReward.getId());
        
        return RewardResponse.from(savedReward);
    }
    
    public RewardResponse createStockReward(Long projectId, StockRewardRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));
        
        // 프로젝트 소유자 확인
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!project.isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        StockReward stockReward = StockReward.create(project, request);
        StockReward savedReward = rewardRepository.save(stockReward);
        
        log.info("주식 리워드가 생성되었습니다. Project ID: {}, Reward ID: {}", projectId, savedReward.getId());
        
        return RewardResponse.from(savedReward);
    }
    
    public RewardResponse createLoanRepaymentReward(Long projectId, LoanRepaymentRewardRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));
        
        // 프로젝트 소유자 확인
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!project.isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        LoanRepaymentReward loanReward = LoanRepaymentReward.create(project, request);
        LoanRepaymentReward savedReward = rewardRepository.save(loanReward);
        
        log.info("P2P 대출 리워드가 생성되었습니다. Project ID: {}, Reward ID: {}", projectId, savedReward.getId());
        
        return RewardResponse.from(savedReward);
    }
    
    public RewardResponse createDonationReward(Long projectId, DonationRewardRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_PROJECT));
        
        // 프로젝트 소유자 확인
        if (!project.isOwnedBy(user.getId())) {
            throw new CustomException(ErrorStatus.NOT_FOUND_PROJECT);
        }
        
        // 프로젝트 수정 가능 상태 확인
        if (!project.isEditable()) {
            throw new CustomException(ErrorStatus.PROJECT_ALREADY_APPROVED);
        }
        
        DonationReward donationReward = DonationReward.create(project, request);
        DonationReward savedReward = rewardRepository.save(donationReward);
        
        log.info("후원 리워드가 생성되었습니다. Project ID: {}, Reward ID: {}", projectId, savedReward.getId());
        
        return RewardResponse.from(savedReward);
    }

    private Reward createBasicDraft(Project project) {
        switch (project.getFundingType()) {
            case P2P_LOAN: return LoanRepaymentReward.createDraft(project);
            case BOND: return BondReward.createDraft(project);
            case STOCK: return StockReward.createDraft(project);
            case DONATION: return DonationReward.createDraft(project);
            default: throw new IllegalArgumentException("지원하지 않는 펀딩 타입");
        }
    }
}