package pl.srb.srb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.srb.srb.model.Lane;
import pl.srb.srb.repository.LaneRepository;

import java.util.List;

@Service
public class LaneService {

    private final LaneRepository laneRepository;

    @Autowired
    public LaneService(LaneRepository laneRepository) {
        this.laneRepository = laneRepository;
    }

    public List<Lane> getAllLanes() {
        return laneRepository.findAll();
    }

    public Lane getLaneById(Long id) {
        return laneRepository.findById(id).orElse(null);
    }

    public Lane saveLane(Lane lane) {
        return laneRepository.save(lane);
    }

    public void deleteLane(Long id) {
        laneRepository.deleteById(id);
    }

    public List<Lane> findAll () {
        return laneRepository.findAll();
    }

    public Lane findById(Long laneId) {
        return laneRepository.findById(laneId).orElse(null);
    }
}
