package net.flandre923.tutorialmod.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ChomperEntity extends Monster  implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public ChomperEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED,1.0f)
                .add(Attributes.MOVEMENT_SPEED,0.4f).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1,new FloatGoal(this));
        this.goalSelector.addGoal(2,new MeleeAttackGoal(this,1.2D,false));
        this.goalSelector.addGoal(4,new WaterAvoidingRandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(5,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2,new NearestAttackableTargetGoal<>(this, Player.class,true));
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this, AbstractVillager.class,false));
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this, IronGolem.class,true));
        this.targetSelector.addGoal(4,new NearestAttackableTargetGoal<>(this, Creeper.class,true));
        super.registerGoals();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event){
        if(event.isMoving()){
            event.getController().setAnimation( new AnimationBuilder().addAnimation("animation.chomper.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.chomper.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;

    }

    private PlayState attackPredicate(AnimationEvent event){
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation( new AnimationBuilder().addAnimation("animation.chomper.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",0,this::predicate));
        data.addAnimationController(new AnimationController(this,"attackController",0,this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void playStepSound(){
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,0.15F,1.0F);
    }

    protected SoundEvent getAmbientSound(){
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource){
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound(){

        return SoundEvents.DOLPHIN_DEATH;
    }

    protected float getSoundVolume(){
        return 0.2F;
    }
}
