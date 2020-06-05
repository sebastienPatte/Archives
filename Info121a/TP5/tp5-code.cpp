#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>

using namespace std;

/* *Definition of an Expression Tree: There are Value- and Operator Nodes. * */

enum Kind   { Value, Operator };
enum OpKind { Mult, Add };

struct Node {
	Kind   kind;
	OpKind data_op;
	int    data_val;
};

typedef vector<Node> Expr;
Expr expr;

void mkValueNode(int n) {
	Node e;
	e.kind     = Value;
	e.data_val = n;
	expr.push_back(e);
}

void mkOperatorNode(OpKind opn) {
	Node e;
	e.kind    = Operator;
	e.data_op = opn;
	expr.push_back(e);
}

// Exemple de fonction traversant des noeuds: la fonction d'affichage.
void print_Node(Node e){
	switch (e.kind) {
		case Value:
			cout << e.data_val << ' ';
			break;
		case Operator:
			switch (e.data_op) {
				case Mult: cout << "* "; break;
				case Add:  cout << "+ "; break;
			};
			break;
	}
}
void print_Expr() {
	for (size_t i = 0; i < expr.size(); i++)
		print_Node(expr[i]);
}

/* Calcul la taille en nombre de noeuds de l'arbre ayant pour racine le noeud
 * [n].
 */
int calc_size_expr(int n) {
	// Pre: 0 <= x < expr.size()
	// TODO
}

/* Renvoie l'index du fils droit du noeud [n].
 */
int get_right(int n) {
	/* Pre: 2 <= x < expr.size() && e.kind == Operator */
	// TODO
}

/* Renvoie l'index du fils gauche du noeud [n].
 */
int get_left(int n) {
	/* Pre: 1 <= x < expr.size() && e.kind == Operator */
	// TODO
}

/* Traverse l'arbre en ordre infixe afin de réaliser un affichage de
 * l'expression ayant pour noeud racine [n].
 */
void print_infix_expr(int n) {
	// Pre: 0 <= n < expr.size()
	// TODO
}

/* Réalise l'evaluation de l'expression ayant pour racine le noeud [n] par un
 * parcours récursif postfixe.
 */
int calc_val_expr(int n) {
	// Pre: 0 <= n < exptr.size()
	// TODO
}

int main(int argc, const char * argv[]) {
	// Défini l'expression "2 * 3 + 4"
	mkValueNode(2);
	mkValueNode(3);
	mkOperatorNode(Mult);
	mkValueNode(4);
	mkOperatorNode(Add);

	cout << "L'expression ";
	print_infix_expr(4);
	cout << " à pour valeur ";
	cout << calc_val_expr(4);
	cout << endl;

	return 0;
}

