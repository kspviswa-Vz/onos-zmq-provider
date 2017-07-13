COMPILE_DEPS = [
    '//lib:CORE_DEPS',
    '//core/api:onos-api',
    '//lib:javax.ws.rs-api',
    '//lib:org.apache.karaf.shell.console',
    '//cli:onos-cli',
    '//lib:jersey-container-servlet',
    '//utils/rest:onlab-rest',
    '//core/store/serializers:onos-core-serializers',
    ':zeromq',
]

BUNDLES = [
    '//apps/zeromqprovider:onos-apps-zeromqprovider',
]

EXCLUDED_BUNDLES = [
    ':zeromq'
]


TEST_DEPS = [
    '//lib:TEST_REST',
    '//web/api:onos-rest-tests',
]

osgi_jar_with_tests (
    deps = COMPILE_DEPS,
    test_deps = TEST_DEPS,
    web_context = '/onos/zeromqprovider',
    api_title = 'Sample app REST API',
    api_package = 'com.vz.onosproject.zeromqprovider',
    api_version = '1.0',
    api_description = 'Sample application REST API',
)

onos_app (
    app_name = 'com.vz.onosproject.zeromqprovider',
    title = 'ZeroMQ Southbound Provider',
    origin = 'Verizon.',
    category = 'Providers',
    url = 'http://onosproject.org',
    description = 'Sample REST API.',
    included_bundles = BUNDLES,
    excluded_bundles = EXCLUDED_BUNDLES,
)

remote_jar (
  name = 'zeromq',
  out = 'jeromq-0.3.5.jar',
  url = 'mvn:org.zeromq:jeromq:jar:0.3.5',
  sha1 = '39a79082570d114bb5433762e836e4dd9c38b03d',
  maven_coords = 'org.zeromq:jeromq:0.3.5',
  visibility = [ 'PUBLIC' ],
)
