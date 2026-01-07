package com.fowobi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fowobi.api.ApiResponse;
import com.fowobi.constatnt.MembershipStatus;
import com.fowobi.dto.*;
import com.fowobi.model.Player;
import com.fowobi.repository.PlayerRepository;
import com.fowobi.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;

    @Value("${upload.directory}")
    private String uploadDir;

    private final ObjectMapper mapper;

    public ApiResponse<String> registerPlayer(PlayerRegRequest request) throws IOException {
        log.info("Player data received: {}", request);
        Player player = new Player();
        player.setPlayerId(UUID.randomUUID().toString());
        player.setFirstname(request.getFirstname());
        player.setMiddlename(request.getMiddlename());
        player.setLastname(request.getLastname());
        player.setPlayerAddress(request.getPlayerAddress());
        player.setPlayerEmail(request.getPlayerEmail());
        player.setPlayerPhone(request.getPlayerPhone());
        player.setDob(request.getDob());
        player.setNationality(request.getNationality());
        player.setOriginState(request.getOriginState());
        player.setHasHealthConcern(request.getHasHealthConcern().equalsIgnoreCase("true"));
        player.setParentPhone(request.getParentPhone());
        player.setParentFirstname(request.getParentFirstname());
        player.setParentMiddlename(request.getParentMiddlename());
        player.setParentLastname(request.getParentLastname());
        player.setParentAddress(request.getParentAddress());
        player.setParentEmail(request.getParentEmail());
        player.setParentTitle(request.getParentTitle());
        player.setRegDate(request.getRegDate());
        player.setHealthConcernDescription(request.getHealthConcernDescription());
        player.setMembershipStatus(MembershipStatus.ACTIVE);

        // create uploads folder if missing
        Files.createDirectories(Paths.get(uploadDir));

        if(request.getPhoto() != null) {
            String filename = UUID.randomUUID() + "_" + request.getPhoto().getOriginalFilename();
            Path filepath = Paths.get(uploadDir, filename);

            Files.write(filepath, request.getPhoto().getBytes());

            player.setPassportPhotoUrl("/uploads/" + filename);
        }

        try {
            repository.save(player);

            return ApiResponse.ok("registered successfully");
        } catch (Exception e) {
            return ApiResponse.error(null,"register operation failed");
        }
    }

    public String updatePlayer(Player player) {
        try {
            repository.save(player);

            return "update successful";
        } catch (Exception e) {
            return "update operation failed";
        }
    }

    public ApiResponse<Page<Player>> getAll(int pageNumber, int pageSize) {

        try {
            Page<Player> playerList = repository.findAll(
                    PageRequest.of(pageNumber,
                            pageSize,
                            Sort.by(Sort.Direction.DESC, "regDate"))
            );

            return ApiResponse.ok(playerList);
        } catch (Exception e) {
            return ApiResponse.notFound(null, "Error while fetching users");
        }

    }


    public ApiResponse<Page<FetchPlayerResponse>> getPlayerSummaries(Pageable pageable) {
        Page<FetchPlayerResponse> allPlayerSummaries = repository.findAllPlayerSummaries(pageable);
        return ApiResponse.ok(allPlayerSummaries);
    }

    @Override
    public ApiResponse<PlayerDto> findByPlayerId(String id) {

        Player player = new Player();

        try {
            player = repository.findByPlayerId(id).orElseThrow(() -> new RuntimeException("Resource not found"));
        } catch (RuntimeException e) {
            return ApiResponse.notFound(null, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(null, e.getMessage());
        }

        PlayerDto playerDto = mapper.convertValue(player, PlayerDto.class);

        return ApiResponse.ok(playerDto);
    }

    @Override
    public ApiResponse<String> uploadPlayerPhoto(PlayerPhotoUpdateRequest request) throws IOException {

        try {
            Player player = repository.findByPlayerId(request.getPlayerId()).orElseThrow(() -> new RuntimeException("Not found"));

            if(player != null) {
                log.info("Player not null");
                // create uploads folder if missing
                Files.createDirectories(Paths.get(uploadDir));

                if(request.getPhoto() != null) {
                    String filename = UUID.randomUUID() + "_" + request.getPhoto().getOriginalFilename();
                    Path filepath = Paths.get(uploadDir, filename);

                    Files.write(filepath, request.getPhoto().getBytes());

                    player.setPassportPhotoUrl("/uploads/" + filename);
                }

                repository.save(player);

                return ApiResponse.ok("Photo saved successfully");
            } else
                log.info("Player not found in repository");

        } catch (Exception e) {
            return ApiResponse.error(null, e.getMessage());
        }

        return null;
    }
}
