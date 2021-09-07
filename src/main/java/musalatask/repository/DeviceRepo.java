package musalatask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import musalatask.model.Device;


@Repository
public interface DeviceRepo extends JpaRepository<Device, Integer>{

}
