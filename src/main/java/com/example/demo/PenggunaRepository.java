package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface PenggunaRepository extends CrudRepository<Pengguna, Long> {

	Pengguna findPenggunaById(Integer userId);
	void deletePenggunaById(Integer userId);
}