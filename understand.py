import understand
db = understand.open('../testdb/test_sonic.udb')

for method in db.ents('method'):
	if method.longname().endswith('run'):
		defining_class = method.ref('DefineIn')
		if defining_class is not None:
			defining_class = defining_class.ent()
			# Couples covers extends and implements
			ancestor_list = [ref.ent().name() for ref in defining_class.refs("Couple")]
			# If Thread is an ancestor_list, report bug
			if 'Thread' in ancestor_list:
				for calling_method in method.refs('call'):
					file_name = calling_method.file()
					line_number = calling_method.line()
					print (file_name, ', line ', line_number, ': Did you intend to use Thread.start() to create a new execution thread?')
			#Else, if Runnable.run() is called from outside executor class
			elif ('Runnable' in ancestor_list) and not ('Executor' in ancestor_list):
				for calling_method in method.refs('call'):
					file_name = calling_method.file()
					line_number = calling_method.line()
					print (file_name, ', line ', line_number, ': Did you intend to put run() method in new thread?')
