package com.sil.bejpa.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 공통엔터티
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class Base {

	@CreatedBy
	@Column(name = "CREATED_BY", updatable = false, length = 30)
	private String createdBy;

	@CreatedDate
	@Column(name = "CREATED_DTTM", updatable = false)
	private LocalDateTime createdDttm;

	@LastModifiedBy
	@Column(name = "MODIFIED_BY", length = 30)
	private String modifiedBy;

	@LastModifiedDate
	@Column(name = "MODIFIED_DTTM")
	private LocalDateTime modifiedDttm;

	@PrePersist
	public void prePersist(){
		LocalDateTime now = LocalDateTime.now();
		this.createdDttm = now;
		this.modifiedDttm = now;
	}

	@PreUpdate
	public void onPreUpdate() {
		this.modifiedDttm = LocalDateTime.now();
	}

}
