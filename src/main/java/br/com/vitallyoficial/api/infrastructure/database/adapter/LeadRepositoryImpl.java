package br.com.vitallyoficial.api.infrastructure.database.adapter;

import br.com.vitallyoficial.api.domain.model.Lead;
import br.com.vitallyoficial.api.domain.model.LeadItem;
import br.com.vitallyoficial.api.domain.model.LeadType;
import br.com.vitallyoficial.api.domain.repository.LeadRepository;
import br.com.vitallyoficial.api.infrastructure.entity.LeadEntity;
import br.com.vitallyoficial.api.infrastructure.entity.LeadItemEntity;
import br.com.vitallyoficial.api.infrastructure.database.repository.LeadJpaRepository;
import br.com.vitallyoficial.api.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LeadRepositoryImpl implements LeadRepository {

    private final LeadJpaRepository leadJpaRepository;

    public LeadRepositoryImpl(LeadJpaRepository leadJpaRepository) {
        this.leadJpaRepository = leadJpaRepository;
    }

    @Override
    public Lead save(Lead lead) {

        LeadEntity entityToSave = toEntity(lead);


        LeadEntity savedEntity = leadJpaRepository.save(entityToSave);


        return toDomain(savedEntity);
    }

    @Override
    public Optional<Lead> findById(UUID id) {
        return leadJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Lead> findAll() {
        return leadJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        leadJpaRepository.deleteById(id);
    }



    private LeadEntity toEntity(Lead lead) {
        LeadEntity entity = new LeadEntity();
        entity.setId(lead.getId());
        entity.setName(lead.getName());
        entity.setPhone(lead.getPhone());
        entity.setEmail(lead.getEmail());
        entity.setMessage(lead.getMessage());
        entity.setCity(lead.getCity());
        entity.setState(lead.getState());
        entity.setType(lead.getType());
        entity.setCreatedAt(lead.getCreatedAt());


        if (lead.getItems() != null && !lead.getItems().isEmpty()) {

            List<LeadItemEntity> itemEntities = lead.getItems().stream()
                    .map(item -> {

                        LeadItemEntity itemEntity = new LeadItemEntity();

                        itemEntity.setProductId(item.getProductId());

                        itemEntity.setQuantity(item.getQuantity());
                        itemEntity.setLead(entity);

                        return itemEntity;

                    }).collect(Collectors.toList());
            entity.setItems(itemEntities);
        }

        return entity;
    }

    private Lead toDomain(LeadEntity entity) {

        List<LeadItem> domainItems = null;
        if (entity.getItems() != null && !entity.getItems().isEmpty()) {

            domainItems = entity.getItems().stream()
                    .map(ie -> new LeadItem(ie.getProductId(), ie.getQuantity()))
                    .collect(Collectors.toList());
        }

        LeadType type = entity.getType();


        if (type == LeadType.CONTACT) {
            return Lead.createContact(entity.getName(), entity.getPhone(), entity.getEmail(), entity.getMessage(), entity.getCity(), entity.getState());
        } else {
            return Lead.createQuote(entity.getName(), entity.getPhone(), entity.getEmail(), entity.getMessage(), entity.getCity(), entity.getState(), domainItems);
        }
    }
}