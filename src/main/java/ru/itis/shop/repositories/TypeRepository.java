package ru.itis.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.shop.models.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}
