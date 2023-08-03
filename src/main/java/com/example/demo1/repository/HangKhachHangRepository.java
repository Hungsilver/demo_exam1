package com.example.demo1.repository;

import com.example.demo1.model.HangKhachHang;
import com.example.demo1.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HangKhachHangRepository extends JpaRepository<HangKhachHang,Long> {
}
