package musalatask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import musalatask.model.Gateway;
@Repository
public interface GatewayRepo extends JpaRepository<Gateway, Integer>{

}
