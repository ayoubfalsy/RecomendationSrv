package com.javatechie.springsecurityjwtexample.repositories;

import com.javatechie.springsecurityjwtexample.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAppUsers_IdOrderByPostIdDesc(long lngId, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE  pays=:strPays and typePost!=:strType and appUsers_id!=:lngId ORDER BY datePub DESC ", nativeQuery = true)
    Page<Post> allPostsByUser(@Param("lngId") long lngId, @Param("strPays") String strPays, @Param("strType") String strType, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE  pays=:strPays and typePost=:strType and appUsers_id!=:lngId ORDER BY datePub DESC", nativeQuery = true)
    Page<Post> allPostsByUserType(@Param("lngId") long lngId, @Param("strPays") String strPays, @Param("strType") String strType, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE  pays!=:strPays and typePost=:strType and appUsers_id!=:lngId ORDER BY datePub DESC", nativeQuery = true)
    Page<Post> allPostsByUserDifPay(@Param("lngId") long lngId, @Param("strPays") String strPays, @Param("strType") String strType, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE  pays!=:strPays and typePost!=:strType and appUsers_id!=:lngId ORDER BY datePub DESC", nativeQuery = true)
    Page<Post> allPostsByUserPaysDifType(@Param("lngId") long lngId, @Param("strPays") String strPays, @Param("strType") String strType, Pageable pageable);

}
