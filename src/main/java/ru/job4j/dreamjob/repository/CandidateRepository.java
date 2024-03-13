package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CandidateRepository implements Repository<Candidate> {
    private static final CandidateRepository INSTANCE = new CandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private CandidateRepository() {
        save(new Candidate(0, "Ivan", "Description 1", LocalDateTime.of(2023, 1, 3, 0, 0)));
        save(new Candidate(0, "Alex", "Description 2", LocalDateTime.of(2023, 2, 3, 0, 0)));
        save(new Candidate(0, "Emily", "Description 3", LocalDateTime.of(2023, 3, 3, 0, 0)));
        save(new Candidate(0, "Anna", "Description 4", LocalDateTime.of(2023, 4, 3, 0, 0)));
        save(new Candidate(0, "Mark", "Description 5", LocalDateTime.of(2023, 5, 3, 0, 0)));
        save(new Candidate(0, "Bob", "Description 6", LocalDateTime.of(2023, 6, 3, 0, 0)));
    }

    public static CandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
        candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (key, oldCandidate) -> new Candidate(key,
                candidate.getName(), candidate.getDescription(), candidate.getCreationDate())) != null;
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
