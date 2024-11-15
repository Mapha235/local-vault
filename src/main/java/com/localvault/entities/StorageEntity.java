package com.localvault.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FileEntity.class, name = "file"),
        @JsonSubTypes.Type(value = FolderEntity.class, name = "folder") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
@Table(name = "STORAGE")
@IdClass(StorageEntityID.class)
@Data
public abstract class StorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @Column(name = "name")
    protected String name;
    @Column(name = "parentDir")
    protected String parentDir;
    @Column(name = "lastModifiedDate")
    protected String lastModifiedDate;

    protected int hashCode;

    public String getAbsolutePath() {
        return this.parentDir + '/' + this.name;
    }

    public int getHashCode() {
        return this.getAbsolutePath().hashCode();
    }
}
