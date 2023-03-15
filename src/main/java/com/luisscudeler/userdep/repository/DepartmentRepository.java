package com.luisscudeler.userdep.repository;

import com.luisscudeler.userdep.entities.Department;
import com.luisscudeler.userdep.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
