/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.persistence.repository.data;


import it.unict.spring.application.persistence.model.data.Data;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> 
{
    List<Data> findByName(String name);
    @Override
    List<Data> findAll();
    @Override
    Data save(Data data);
    @Override
    void delete(Data data);
}