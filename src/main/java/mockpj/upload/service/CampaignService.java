package mockpj.upload.service;

import mockpj.upload.model.Campaign;
import mockpj.upload.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;

    public Optional<Campaign> findCampaignById(Long id){
        return campaignRepository.findById(id);
    }
}
