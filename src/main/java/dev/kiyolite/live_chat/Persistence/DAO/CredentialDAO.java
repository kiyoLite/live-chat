/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.kiyolite.live_chat.Persistence.DAO;

import dev.kiyolite.live_chat.Entities.DB.Credential;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author soyky
 */
public interface CredentialDAO extends JpaRepository<Credential, Long> {
}
