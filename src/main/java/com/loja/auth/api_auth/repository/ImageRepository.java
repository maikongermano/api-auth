package com.loja.auth.api_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.auth.api_auth.model.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
