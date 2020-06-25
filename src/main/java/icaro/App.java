package icaro;

import icaro.api.Trends;
import icaro.dao.TrendsDao;
import icaro.resource.TrendsResource;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;


public class App extends Application<Configuration> {
    public static void main( String[] args ) {
        try{
            (new App()).run(args);
        } catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        //!QNDO RODA ISSO A PAGINA NAO CARREGA!
        //AssetsBundle assetsBundle = new AssetsBundle("/site", "/", "index.html");
        //bootstrap.addBundle(assetsBundle);
    }

    @Override
    public void run(Configuration configuration, Environment environment)  {
/*
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        //CONFIGURE CORS PARAMETROS
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
*/
        //registrando recursos
        TrendsDao dao = new TrendsDao();
        TrendsResource trendsResource = new TrendsResource(dao);
        environment.jersey().register(trendsResource);

        //!QNDO ATIVA ISSO A PAGINA NAO CARREGA!
        //environment.jersey().setUrlPattern("/api/*");
    }
}
