package com.fowobi.service;

import com.fowobi.dto.AwardIssuanceRequest;
import com.fowobi.model.Award;
import com.fowobi.repository.AwardRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AwardService {

    private static final Logger log = LoggerFactory.getLogger(AwardService.class);
    private final AwardRepository awardRepository;

    public AwardService(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    public String addAward(AwardIssuanceRequest awardRequest) {

        Award award = new Award();
        award.setTitle(awardRequest.getTitle());
        award.setDescription(awardRequest.getDescription());
        award.setIssuer(awardRequest.getIssuer());
        award.setDate(awardRequest.getDateIssued());

        try {
            Award saved = awardRepository.save(award);
            log.info(saved.toString());
            return "Saved successfully";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public List<Award> getAllAwards() {
        return awardRepository.findAll();
    }

    public Award getById(long id) {
        return awardRepository.findById(id).orElseThrow();
    }
}
