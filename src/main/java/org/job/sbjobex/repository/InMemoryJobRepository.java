package org.job.sbjobex.repository;

import org.job.sbjobex.model.JobEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryJobRepository {

    private final Map<Long, JobEntity> jobStore = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public JobEntity save(JobEntity job) {
        if (job.getId() == null) {
            job.setId(idGenerator.getAndIncrement());
        }
        jobStore.put(job.getId(), job);
        return job;
    }

    public Optional<JobEntity> findById(Long id) {
        return Optional.ofNullable(jobStore.get(id));
    }

    public Optional<JobEntity> findByEmail(String email) {
        return jobStore.values().stream()
                .filter(job -> job.getEmail().equals(email))
                .findFirst();
    }

    public List<JobEntity> findAll() {
        return new ArrayList<>(jobStore.values());
    }
}