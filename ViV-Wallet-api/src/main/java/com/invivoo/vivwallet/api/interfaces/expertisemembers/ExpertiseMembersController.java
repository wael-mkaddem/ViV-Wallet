package com.invivoo.vivwallet.api.interfaces.expertisemembers;

import com.invivoo.vivwallet.api.domain.expertise.Expertise;
import com.invivoo.vivwallet.api.domain.expertise.ExpertiseMember;
import com.invivoo.vivwallet.api.domain.expertise.ExpertiseMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ExpertiseMembersController.API_V1_EXPERTISES)
public class ExpertiseMembersController {

    static final String API_V1_EXPERTISES = "/api/v1/expertises";
    private final ExpertiseMemberRepository expertiseMemberRepository;

    @Autowired
    public ExpertiseMembersController(ExpertiseMemberRepository expertiseMemberRepository) {
        this.expertiseMemberRepository = expertiseMemberRepository;
    }

    @GetMapping("/{expertiseName}/members")
    public ResponseEntity<List<ExpertiseMember>> getExpertiseMembers(@PathVariable("expertiseName") Expertise expertise) {
        List<ExpertiseMember> expertiseMembers = expertiseMemberRepository.findAll();
        return ResponseEntity.ok(expertiseMembers);
    }

    @PostMapping("/{expertiseName}/members")
    public ResponseEntity<ExpertiseMember> postExpertiseMember(@PathVariable("expertiseName") Expertise expertise, @RequestBody ExpertiseMember expertiseMember) {
        ExpertiseMember savedExpertiseMember = expertiseMemberRepository.save(expertiseMember);
        return ResponseEntity.created(getLocation(expertise, savedExpertiseMember))
                .body(expertiseMember);
    }

    @GetMapping("/{expertiseName}/members/{id}")
    public ResponseEntity<ExpertiseMember> getExpertiseMember(@PathVariable("expertiseName") Expertise expertise, @PathVariable("id") Long expertiseMemberId) {
        return expertiseMemberRepository.findById(expertiseMemberId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PutMapping("/{expertiseName}/members/{id}")
    public ResponseEntity<ExpertiseMember> putExpertiseMember(@PathVariable("expertiseName") Expertise expertise, @PathVariable("id") Long expertiseMemberId, @RequestBody ExpertiseMemberUpdateDTO expertiseMemberUpdate) {
        return expertiseMemberRepository.findById(expertiseMemberId)
                .map(expertiseMember -> updateExpertiseMember(expertiseMember, expertiseMemberUpdate))
                .map(expertiseMemberRepository::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    private URI getLocation(Expertise expertise, ExpertiseMember savedExpertiseMember) {
        return URI.create(String.format("%s/%s/members/%s", API_V1_EXPERTISES, expertise.toString(), savedExpertiseMember.getId()));
    }

    private ExpertiseMember updateExpertiseMember(ExpertiseMember expertiseMember, ExpertiseMemberUpdateDTO expertiseMemberUpdate) {
        expertiseMember.setStatus(expertiseMemberUpdate.getStatus());
        expertiseMember.setStartDate(expertiseMemberUpdate.getStartDate());
        expertiseMember.setEndDate(expertiseMemberUpdate.getEndDate());
        return expertiseMember;
    }
}

