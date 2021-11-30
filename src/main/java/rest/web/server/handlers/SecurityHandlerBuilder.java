package rest.web.server.handlers;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.util.security.Constraint;

import java.util.*;
import java.util.stream.Collectors;

public final class SecurityHandlerBuilder {
    private static final String ROLE_MANAGER = "manager";
    private static final String ROLE_GUEST = "guest";
    private final Collection<String> GET_HEAD_OPTIONS = Arrays.asList("GET","HEAD", "OPTIONS");

    private final ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();

    public ConstraintSecurityHandler build(LoginService loginService) {
        securityHandler.setLoginService(loginService);

        final List<ConstraintMapping> constraintMappings = new ArrayList<>();

        constraintMappings.addAll(constraintMethodsMapping(
                buildConstraint(ROLE_MANAGER, ROLE_GUEST),
                Arrays.asList("/", "/products/*"),
                GET_HEAD_OPTIONS
        ));

        constraintMappings.addAll(constraintMethodsMapping(
                buildConstraint(ROLE_MANAGER),
                Arrays.asList("/", "/products/*"),
                Collections.singletonList("POST")
        ));

        securityHandler.setConstraintMappings(constraintMappings);
        securityHandler.setAuthenticator(new BasicAuthenticator());
        securityHandler.setDenyUncoveredHttpMethods(true);
        return securityHandler;
    }

    private static Constraint buildConstraint(String... userRoles) {
        final Constraint starterConstraint = new Constraint();
        starterConstraint.setName(Constraint.__BASIC_AUTH);
        starterConstraint.setRoles(userRoles);
        starterConstraint.setAuthenticate(true);
        return starterConstraint;
    }

    private static Collection<ConstraintMapping> constraintMethodsMapping(Constraint constraint,
                                                                      Collection<String> paths,
                                                                      Collection<String> methods) {
        Collection<ConstraintMapping> constraintMappings = new ArrayList<>();
        for (String method: methods) {
            constraintMappings.addAll(constraintMapping(constraint, paths, method));
        }
        return constraintMappings;
    }

    private static Collection<ConstraintMapping> constraintMapping(Constraint constraint,
                                                                   Collection<String> paths,
                                                                   String method) {
        return paths.stream()
                .map(path -> {
                            final ConstraintMapping mapping = new ConstraintMapping();
                            mapping.setConstraint(constraint);
                            mapping.setPathSpec(path);
                            mapping.setMethod(method);
                            return mapping;
                        }
                ).collect(Collectors.toList());
    }
}
