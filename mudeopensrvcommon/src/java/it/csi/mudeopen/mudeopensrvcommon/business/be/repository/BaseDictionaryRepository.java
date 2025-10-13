/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
@NoRepositoryBean
public interface BaseDictionaryRepository<T, ID extends Serializable> extends JpaRepository<T, String> {

    List<T> findAll(Specification<T> specs);

    List<T> findAll(Specification<T> specs, Sort sort);

    Page<T> findAll(Specification<T> specs, Pageable pageable);
}