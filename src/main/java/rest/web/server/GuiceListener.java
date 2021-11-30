package rest.web.server;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import rest.web.server.api.BaseContentREST;
import rest.web.server.api.ProductsREST;
import rest.web.server.content.managers.ContentGenerator;
import rest.web.server.content.managers.ProductsDBManager;
import rest.web.server.dao.ProductDAO;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("NotNullNullableValidation")
public final class GuiceListener extends GuiceResteasyBootstrapServletContextListener {

  @Override
  protected List<? extends Module> getModules(ServletContext context) {
    return Collections.singletonList(new GuiceModule());
  }

  @SuppressWarnings("rawtypes")
  private static final class GuiceModule extends AbstractModule {
    @SuppressWarnings("PointlessBinding")
    @Override
    protected void configure() {
      bind(ProductDAO.class);
      bind(ProductsDBManager.class);
      bind(ContentGenerator.class);
      bind(ProductsREST.class);
      bind(BaseContentREST.class);
    }
  }
}
