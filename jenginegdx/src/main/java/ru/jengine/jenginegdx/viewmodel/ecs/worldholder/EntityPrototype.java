package ru.jengine.jenginegdx.viewmodel.ecs.worldholder;

import ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading.EntityDto;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;

public record EntityPrototype(EntityDto prototypeDefinition, EntityLinkingInfo linkingContext) { }
