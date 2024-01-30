package org.galatea.starter;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

  /**
   * required method.
   * @return key space
   */
  @NonNull
  @Override
  protected String getKeyspaceName() {
    return "spring_cassandra";
  }

  /**
   * testing.
   * @return testing
   */
  @NonNull
  @Bean
  public CassandraClusterFactoryBean cluster() {
    CassandraClusterFactoryBean cluster =
        new CassandraClusterFactoryBean();
    cluster.setContactPoints("0.0.0.0");
    cluster.setPort(9042);
    return cluster;
  }

  /**
   * testing.
   * @return testing
   */
  @NonNull
  @Bean
  public CassandraMappingContext cassandraMapping()
      throws ClassNotFoundException {
    return new BasicCassandraMappingContext();
  }
}