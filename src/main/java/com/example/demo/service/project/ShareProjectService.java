package com.example.demo.service.project;

import com.example.demo.dto.model.project.SharedProjectRequest;
import com.example.demo.dto.model.project.SharedProjectResponse;
import com.example.demo.repository.project.ShareProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ShareProjectService {

    private final ShareProjectRepository shareProjectRepository;

    public ShareProjectService(ShareProjectRepository shareProjectRepository) {
        this.shareProjectRepository = shareProjectRepository;
    }

    public SharedProjectResponse shareProject(Long projectId, SharedProjectRequest req) {
        if (req.getUserId().equals(projectId)) {
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
