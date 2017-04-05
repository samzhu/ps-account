package com.ps.processor;

import com.ps.controller.AccountRoleController;
import com.ps.model.Account;
import com.ps.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by samchu on 2016/12/2.
 */
@Component
public class AccountResourcesProcessor implements ResourceProcessor<Resource<Account>> {

    @Autowired
    private RepositoryEntityLinks repositoryEntityLinks;

    @Override
    public Resource<Account> process(Resource<Account> resource) {
        //resource.add(repositoryEntityLinks.linksToSearchResources(Role.class).getLink("findRoleListByUserAccountid").withRel("roles")); // /roles/search/findRoleListByUserAccountid{?accountid}
        //resource.add(repositoryEntityLinks.linksToSearchResources(Role.class).getLink("findRoleListByUserUsername").withRel("roles"));
        //resource.add(repositoryEntityLinks.linkToCollectionResource(Role.class).expand("5","20").withRel("roles")); // roles?page=5&size=20
        resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AccountRoleController.class).findRoleByAccountid(resource.getContent().getAccountid())).withRel("roles"));
        return resource;
    }
}
