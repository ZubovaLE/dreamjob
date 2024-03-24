package ru.job4j.dreamjob.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Ivan", "Description 1", LocalDateTime.of(2023, 1, 3, 0, 0), 1, 0));
        save(new Candidate(0, "Alex", "Description 2", LocalDateTime.of(2023, 2, 3, 0, 0), 2, 0));
        save(new Candidate(0, "Emily", "Description 3", LocalDateTime.of(2023, 3, 3, 0, 0), 3, 0));
        save(new Candidate(0, "Anna", "Description 4", LocalDateTime.of(2023, 4, 3, 0, 0), 1, 0));
        save(new Candidate(0, "Mark", "Description 5", LocalDateTime.of(2023, 5, 3, 0, 0), 2, 0));
        save(new Candidate(0, "Bob", "Description 6", LocalDateTime.of(2023, 6, 3, 0, 0), 3, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (key, oldCandidate) -> new Candidate(key,
                candidate.getName(), candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId(),
                candidate.getFileId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
