package com.yeliheng.blogsystem.dao;

import com.yeliheng.blogsystem.domain.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftRepository extends JpaRepository<Draft,Long> {

}
