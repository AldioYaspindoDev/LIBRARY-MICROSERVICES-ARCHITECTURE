package com.aldio.anggota.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.mongodb.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // PERBAIKAN: Anotasi untuk JPA (PostgreSQL)
@Table(name = "anggota") // PERBAIKAN: Menentukan nama tabel di 
public class AnggotaCommand implements Serializable{
    @Id
    @NonNull private String id;

    private String nim;
    private String nama;
    private String alamat;
    private String jenis_kelamin;
    private LocalDateTime CreatedAt;

    @Transient
    private EventType eventType;

    public enum EventType{
        CREATED,
        UPDATED,
        DELETED
    }
}