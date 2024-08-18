package ba.sum.fsre.ednevnik.services;

import ba.sum.fsre.ednevnik.models.Pitanja;
import ba.sum.fsre.ednevnik.repositories.PitanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PitanjaService {
    @Autowired
    PitanjaRepository pitanjaRepository;

    public Pitanja savePitanja(Pitanja pitanja){
        return pitanjaRepository.save(pitanja);
    }
}
