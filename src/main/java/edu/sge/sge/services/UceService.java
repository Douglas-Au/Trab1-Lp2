package edu.sge.sge.services;

import edu.sge.sge.models.UCE;
import edu.sge.sge.repository.UceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UceService {

    @Autowired
    private UceRepo uceRepo;

    public List<UCE> getAll() {
        return uceRepo.findAll();
    }

    public Optional<UCE> getById(Long id) {
        return uceRepo.findById(id);
    }

    public List<UCE> getByPpc(Long ppcId) {
        return uceRepo.findByPpcId(ppcId);
    }

    public UCE create(UCE uce) {
        return uceRepo.save(uce);
    }

    public UCE update(Long id, UCE novaUce) {
        novaUce.setId(id);
        return uceRepo.save(novaUce);
    }

    public void delete(Long id) {
        uceRepo.deleteById(id);
    }
}
