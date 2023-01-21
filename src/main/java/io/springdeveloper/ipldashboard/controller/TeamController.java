package io.springdeveloper.ipldashboard.controller;

import java.time.LocalDate;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.springdeveloper.ipldashboard.model.Match;
import io.springdeveloper.ipldashboard.model.Team;

import io.springdeveloper.ipldashboard.repository.MatchRepository;
import io.springdeveloper.ipldashboard.repository.TeamRepository;


@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;
    
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }


    @GetMapping("/team")
    public Iterable<Team> getAllTeam() {
        return this.teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName,4));
            
        return team;
    }
    @GetMapping("/team/{teamName}/matches")
    public List<Match>getMatchesForTeam(@PathVariable String teamName, @ RequestParam int year)
    {
    LocalDate startDate=LocalDate.of(year, 1, 1);
    LocalDate endDate=LocalDate.of(year+1, 1, 1);
    return this.matchRepository.getMatchsByTeamBetweenDates(teamName,
    startDate, endDate);
    }
}
