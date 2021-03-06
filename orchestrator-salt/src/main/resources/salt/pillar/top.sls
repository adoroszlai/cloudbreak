base:
  'G@roles:ambari_server or G@roles:ambari_agent':
    - match: compound
    - ambari.config
    - ambari.repo
    - ambari.gpl
    - hdp.repo

  '*':
    - network
    - nodes.hosts
    - discovery.init
    - recipes.init
    - unbound.forwarders
    - datalake.init
    - docker
    - metadata.init
    - proxy.proxy

  'G@roles:ad_member or G@roles:ad_leave':
    - match: compound
    - sssd.ad

  'G@roles:ipa_member or G@roles:ad_leave':
    - match: compound
    - sssd.ipa

  'roles:gateway':
    - match: grain
    - gateway.init
    - gateway.ldap

  'roles:kerberized':
    - match: grain
    - kerberos.init

  'roles:postgresql_server':
    - match: grain
    - postgresql.postgre

  'roles:manager_server':
    - match: grain
    - cloudera-manager.database

  'roles:ambari_server*':
    - match: grain
    - ambari.database
    - ambari.credentials
    - ambari.ldaps
    - grafana.repo
    - gateway.init
    - gateway.ldap
    - jdbc.connectors

  'roles:knox_gateway':
    - match: grain
    - ldap.init

  'roles:smartsense_agent_update':
    - match: grain
    - smartsense

  'roles:smartsense':
    - match: grain
    - smartsense
    - smartsense.credentials
