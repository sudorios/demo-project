package com.example.demo.service.project;

import com.example.demo.dto.model.project.SharedProjectRequest;
import com.example.demo.dto.model.project.SharedProjectResponse;
import com.example.demo.repository.project.ShareProjectRepository;
import com.example.demo.util.ProjectUtil;

import org.springframework.stereotype.Service;

@Service
public class ShareProjectService {

    private final ShareProjectRepository shareProjectRepository;
    private final ProjectUtil projectUtil;

    public ShareProjectService(ShareProjectRepository shareProjectRepository, ProjectUtil projectUtil) {
        this.shareProjectRepository = shareProjectRepository;
        this.projectUtil = projectUtil;
    }

    public SharedProjectResponse shareProject(Long loggedUserId, Long projectId, SharedProjectRequest req) {
        Long ownerUserId = projectUtil.getProjectOwner(projectId);
        if (req.getUserId().equals(ownerUserId)) {
            throw new IllegalArgumentException("❌ Un proyecto no puede compartirse consigo mismo.");
        }
        shareProjectRepository.shareProject(projectId, req);
        return new SharedProjectResponse(
                projectId,
                req.getUserId(),
                "ROL_ID=" + req.getRoleId(),
                "PERM_CODE=" + req.getPermissionCode(),
                "✅ Proyecto compartido exitosamente");
    }

}
