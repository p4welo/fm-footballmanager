package com.fm.domain;

import javax.persistence.*;

/**
 * UserEntity: pawel
 * Date: 13.12.12
 * Time: 23:14
 */
@Entity
@Table(name = PlayerStats.TABLE_NAME)
public class PlayerStats extends IdentifiableEntity
{
   public static final String TABLE_NAME = "player_stats";

   @Id
   @GeneratedValue
   @Column
   private Long id;

   private Integer goalkeeping;

   private Integer defence;

   private Integer passing;

   private Integer strike;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }
}
