package attack;
public class BasicAttack implements IAttack {

	private int attackDamage;
	private double attackDistance;
	
	public BasicAttack(int attackDamage, double attackDistance) {
		this.attackDamage = attackDamage;
		this.attackDistance = attackDistance;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public Double getAttackDistance() {
		return attackDistance;
	}

	public void setAttackDistance(double attackDistance) {
		this.attackDistance = attackDistance;
	}

}
